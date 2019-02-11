# 负责与"投票"按钮相关的逻辑
class VotersController < ApplicationController

  before_action :throttle

  # 更新投票信息
  #   1, model的投票数+1
  #   2, 投票者的相关操作(可投票model, 抽检数, 剩余投票权)
  # 返回值
  # {status: 0, vote_num: current_num} current_num: 投票结束后的投票数量
  # {status: 1, msg: error_msg}
  def vote
    openid = session[:weixin_openid]
    model_id = params[:id]
    @user = User.find model_id
    @voter = Voter.find_by openid: openid

    # 假如没有投票者的信息, 则新建一个表单
    unless @voter
      # 假如为空, 则创建该投票用户
      @voter = Voter.new
      @voter.openid = openid
      @voter.current_date = Date.current #
      @voter.vote_id = model_id #
      @voter.lottery_times = 1 #

      if @voter.save
        @user.update(vote_num: @user.vote_num + 1)
        record openid, model_id
        render json: {status: 0}
      else
        render json: {status: 1}
      end
    else
      current_date = Date.current
      if current_date > @voter.current_date
        # 假如存在投票的人, 并且上次投票时间已为昨天
        @voter.current_date = current_date
        @voter.vote_id = model_id #
        @voter.lottery_times = 1 #
        if @voter.save
          @user.update(vote_num: @user.vote_num + 1)
          record openid, model_id
          render json: {status: 0, vote_num: @user.vote_num}
        else
          render json: {status: 1}
        end
      elsif current_date == @voter.current_date
        # 假如投票已投票

        vote_id = @voter.vote_id

        if vote_id.present?
          vote_ids = vote_id.split(',')
          if vote_ids.size >= 3 # 可投票次数
            render json: {status: 1, msg: '你今天的投票权限已使用完毕啦 ( 明天见 ) '}
          elsif vote_ids.include?(model_id)
            render json: {status: 1, msg: '今天你已经为该模特投票了'}
          else
            vote_ids << model_id
            ActiveRecord::Base.transaction do
              @voter.update!(vote_id: vote_ids.join(','), lottery_times: (@voter.lottery_times + 1))
              @user.update!(vote_num: @user.vote_num + 1)
              record openid, model_id
              render json: {status: 0, vote_num: @user.vote_num}
            end
          end
        else
          ActiveRecord::Base.transaction do
            # 在此之前没有投过票
            @voter.update!(vote_id: model_id, lottery_times: (@voter.lottery_times + 1))
            @user.update!(vote_num: @user.vote_num + 1)
            record openid, model_id
            render json: {status: 0, vote_num: @user.vote_num}
          end
        end
      else
        render json: {status: 1, msg: 'error'}
      end
    end
  end

  private
  def record openid, vote_id
    @record = Record.new
    @record.openid = openid
    @record.date = Date.current
    @record.vote_id = vote_id
    @record.save
  end

  def throttle

    user_id = params[:id].strip
    h_key = "Limit:Ua:#{user_id}"
    had_key = "Limited:Ua:#{user_id}"
    h_field = request.user_agent

    time = $redis.hget(had_key, h_field)
    if time

      if Date.current > time.to_date
        $redis.hdel(had_key, h_field)
      else
        Rails.logger.error("#{session[:weixin_openid]}|-#{Time.now}|-#{params[:id]}")
        render json: {status: 1, msg: '投票异常请稍后重试！'}
      end

    else

      times = $redis.hget(h_key, h_field).to_i
      if times == 0
        $redis.multi do
          $redis.del(h_key)
          $redis.hset(h_key, h_field, 1)
          $redis.expire(h_key, DateTime.now.seconds_until_end_of_day)
        end
      elsif times >= 15
        $redis.multi do
          $redis.del(h_key)
          $redis.hset(had_key, h_field, Date.current)
        end

        Rails.logger.error("#{session[:weixin_openid]}|-#{Time.now}|-#{params[:id]}")
        render json: {status: 1, msg: '投票异常请稍后重试！'}
      else
        $redis.hincrby(h_key, h_field, 1)
      end
    end

    #   client_ips =  request.remote_ip.split(',').map(&:strip)
    #   ips = []
    #   client_ips.each do|ip|
    #     ips << ip if ip =~ /\A(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\z/
    #   end
    #   case $redis.get('Cute:'+ips[0]).to_i
    #     when 6
    #       render json: { status: 1 }
    #     when 0
    #       $redis.setex('Cute:'+ips[0], DateTime.now.seconds_until_end_of_day, 1)
    #     else
    #       $redis.incr('Cute:'+ips[0])
    #   end
    #
    #   #限制获取最大票数
    #   voted_id = params[:id]
    #   case times = $redis.get(voted_id).to_i
    #     when 100
    #       render json: { status: 1 }
    #     else
    #       $redis.setex(ips[0], 900, times + 1)
    #   end
  end
end
