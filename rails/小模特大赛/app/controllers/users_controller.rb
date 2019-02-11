# 负责用户的注册, 提交, 查询等信息的逻辑
#require 'zxing'
class UsersController < ApplicationController
  require 'base64'

  # get 系列操作
  # 提交model 基本信息, 并将其保存到数据库中
  def commit
    @user = User.new user_params
    # 无论前端提交数据为何, vote_num初始的值总为0
    @user.vote_num = 0
    if @user.save!
      render json: {status: 0, id: @user.id}
    else
      render json: { status: 1, msg: '提交失败, 数据格式不正确, 若没能得到正确提示, 请联系工作人员'}
    end
  end

  # 根据不同的条件返回不同的查询结果
  def profile
    method = params[:method]
    page = params[:page].to_i || 1
    case method
      #   执行查找方法
      when 'search'
      key = params[:key]
      value = params[:value]
      case key
        when 'id'
          tempUser = User.where(id: value)
        when 'name'
          tempUser = User.where('name like ?',"#{value}%")
          #tempUser = User.where(name: value)
        when 'phone'
          tempUser = User.where(phone: value)
      end
      # 执行排名方法
      when 'rank'
          tempUser = User.order("vote_num desc")
      when 'show'
        tempUser = User
      else
        render json: {status: 1, msg: 'method: 查询参数错误'}
    end

    # 分页代码
    @users = tempUser.order('created_at DESC').page(page).per(6)
    total_num = tempUser.count
    total_page = total_num % 6 == 0 ? total_num / 6 : total_num / 6 + 1

    # 获取用户是否对该模特的状态(是否已经投票)
    openid = session[:weixin_openid]
    @voter = Voter.find_by openid: openid
    if @voter.blank? || @voter.vote_id.blank?
      # 假如没有id, 则返回空白数组
      vote_ids = []
    else
      p @voter.vote_id
      vote_ids = @voter.vote_id.split(',')
    end

    # 因为相同名字或电话号码人数较少,这个就不分页了
    render json: {page: page, totalPage: total_page, users: @users, voteIDs: vote_ids}
  end

  # 返回单个model信息
  def each
    @user = User.find(params[:id])

    openid = session[:weixin_openid]
    @voter = Voter.find_by openid: openid
    isVote = false # 是否为该模特投过票了

    if @voter
      vote_id = @voter.vote_id
      if vote_id.present?
        isVote = true if vote_id.split(',').include?(params[:id])
      end
    end

    render json: {user: @user, isVote: isVote}
  end

  # post 系列操作
  # 上传图片
  def upload

    Rails.logger.error("**********start upload open_id#{session[:weixin_openid]} #{Time.now} *************")
    uploader = AvatarUploader.new

    # base64 解码
    decoded_file = Base64.decode64(params[:file])
    file = Tempfile.new(['image', '.jpeg'])
    file.binmode
    file.write decoded_file

    max_size = 3 << 20 # 3 * 2^20
    if file.size > max_size
      # 假如图片是传到数据库中, 可以考虑model进行size认证
      # ref: https://github.com/carrierwaveuploader/carrierwave/wiki/How-to%3A-Validate-attachment-file-size
      render json: {status: 1, msg: '手机太先进了 /(ㄒoㄒ)/, 图片过大(>3MB)'}
    else
      if !file.blank?
        # 假如是二次上传,应该删除原先的文件
        if uploader.store!(file)
          #if ZXing.decode Rails.root.to_s + '/public' + uploader.url
          #  Rails.logger.error("**********ip #{request.headers['X-Forwarded-For']} #{file.size}*************")
          #  render json: {status: 1, msg: '图片上传失败'}
          #else
            Rails.logger.error("**********start success#{Time.now} #{file.size}*************")
            render json: {status: 0, filename: uploader.filename}
          #end
        else
          Rails.logger.error("**********start error #{file.size}*************")
          render json: {status: 1, msg: '图片上传失败'}
        end
      else
        render json: {status: 1, msg: '请确保至少上传一张图片'}
      end
    end
  end

  private
  def user_params
    # 根据ruby guid的说法,不推荐在controller层进行验证
    # 1. 对于格式类型, 如string, int, 应归类为migrate层操作
    # 2. 对于非空类型, 如presence, 应归类为model层操作
    # 姓名, 性别, 电话号码, 地址, 图片路径, 宣言, 投票数
    # 投票数应在此处进行设置
    params.require(:user).permit(:name, :sex, :phone, :address, :first_img_name, :second_img_name, :third_img_name, :slogan, :vote_num)
  end
end