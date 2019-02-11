class AdminController < ActionController::Base
  layout :false

  def admin
    ips = request.headers['X-Forwarded-For']
    if ips =~ /192\.168\.200\..*/
      render plain: 'error'
    else
      @redeems = Redeem.all
      render template: '/pages/admin.html'
    end
  end

  # 查询中奖名单
  def result
    ips = request.headers['X-Forwarded-For']
    if ips =~ /192\.168\.200\..*/
      render plain: 'error'
    else
      @redeems = Redeem.all
      render template: '/pages/result.html.erb'
    end
  end

  # 后台(韩都内网)不需要微信认证也可获取用户信息
  def each
    if permit_ip?
      if User.exists?(params[:id])
        render json: {status: 0, user: User.find(params[:id])}
      else
        render json: {status: 1, msg: '不存在该参赛ID'}
      end
    else
      render json: {status: 1, msg: '你没有操作权限'}
    end
  end

  # 更新童心
  def update
    if permit_ip?
      u = User.find params[:id]
      if params[:name].present?
        u.name = params[:name]
      end
      if params[:phone].present?
        u.phone = params[:phone]
      end
      if params[:sex].present?
        u.sex = params[:sex]
      end
      if params[:slogan].present?
        u.slogan = params[:slogan]
      end
      if params[:address].present?
        u.address = params[:address]
      end
      if u.save
        render json: {status: 0}
      else
        render json: {status: 1}
      end
    else
      render plain: 'error'
    end
  end

  # 删除童心
  def delete
    if permit_ip?
      u = User.find params[:id]
      if u.destroy
        render json: {status: 0}
      else
        render json: {status: 1}
      end
    else
      render plain: 'error'
    end
  end

  # 仅允许内网访问
  # 当属于内网时
  private
  def permit_ip?
    ips = request.headers['X-Forwarded-For']
    if ips =~ /192\.168\.200\..*/
      return false
    else
      return true
    end
  end
end
