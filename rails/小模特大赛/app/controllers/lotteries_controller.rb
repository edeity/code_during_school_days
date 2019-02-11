# 负责抽奖页面的逻辑
class LotteriesController < ApplicationController
  # 抽奖
  def lottery
    openid = session[:weixin_openid]
    @voter = Voter.where(openid: openid).take

    if @voter.blank?
      render json: {status: 1, msg: '小主, 为喜欢的参赛者投上一票, 就可参与抽奖啦~'}
    elsif @voter.lottery_times == 0
      if @voter.vote_id.present? && @voter.vote_id.split(',').size >= 3
        render json: {status: 1, msg: '小主~您今天的抽奖名额用完喽, 明天见'}
      else
        render json: {status: 1, msg: '小主, 抽奖名额已用完, 继续投票才可以获取更多抽奖名额哦~'}
      end
    else
      ActiveRecord::Base.transaction do
        # 抽奖逻辑
        @voter.update!(lottery_times: @voter.lottery_times - 1)
        get_virtual_lottery
      end
    end
  end

  private
  # def get_lottery
  #   total_price_num = Lottery.sum('lottery_num')
  #   @entity_lottery = Lottery.find_by lottery_name: '时尚童装'
  #   entity_num = @entity_lottery.lottery_num
  #   # 假如还有该奖品
  #   if (entity_num > 0)
  #     if (rand(total_price_num) <= entity_num) # 假如抽奖次数达到3W, 10件衣服必定是会被抽完的
  #       # 更新奖品数 量
  #       @entity_lottery.lottery_num -= 1
  #       @entity_lottery.update(lottery_num: @entity_lottery.lottery_num)
  #       # 1/ 30000的概率获得时尚童装
  #       redeem_code = SecureRandom.uuid.upcase
  #       @redeem = Redeem.new
  #       @redeem.redeem_code = redeem_code
  #       @redeem.save
  #       render json: {status: 0, lottery_key: @entity_lottery.lottery_key, redeem_code: redeem_code}
  #     else
  #       get_virtual_lottery
  #     end
  #   else
  #     get_virtual_lottery
  #   end
  # end

  # 存在用户,并且可以抽奖
  # MZ 10元无门槛优惠券 1 2500 10%
  # MZ 满399减100优惠券 2 25000 49%
  # HP 10元无门槛优惠券 3 2500 10%
  # 果芽 10元无门槛优惠券 4 2500 10%
  # 时尚童装 5 5
  # 嘉华旅游 200-20 6 10%
  # 嘉华旅游 499-100 7 1%
  # 谢谢参与 8 10%

  private
  def get_virtual_lottery
    # 虚拟奖品按照概率来, 减少对数据库操作
    random_key = rand(100)
    case random_key
      when 0..10
        @virtual_lottery = Lottery.find_by lottery_key: 1
      when 11..59
        @virtual_lottery = Lottery.find_by lottery_key: 2
      when 60..69
        @virtual_lottery = Lottery.find_by lottery_key: 3
      when 70..79
        @virtual_lottery = Lottery.find_by lottery_key: 4
      when 80..89
        render json: {status: 0, lottery_key: 8} # 谢谢参与
        return
      when 90..99
        @virtual_lottery = Lottery.find_by lottery_key: 6
      when 100
        @virtual_lottery = Lottery.find_by lottery_key: 7
    end
    # 获取奖品类型,但在此之前得确定是否还存在该奖品
    @virtual_lottery.lottery_num -= 1
    @virtual_lottery.update(lottery_num: @virtual_lottery.lottery_num)
    render json: {status: 0, lottery_key: @virtual_lottery.lottery_key}
  end
end
