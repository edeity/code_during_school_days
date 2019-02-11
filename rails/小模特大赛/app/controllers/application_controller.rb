class ApplicationController < ActionController::Base

  # 一下功能为开启微信认证
  # before_action :authenticate_openid!
  before_action :test_openid!

  private
  def test_openid!
    if session[:weixin_openid].blank?
      # 随机产生一个数值作为用户标示
      session[:weixin_openid] = rand(9999)
    end
  end

  private
  def authenticate_openid!
    # 经过两次跳转拿到用户的openid
    # 注意, 第一次跳转拿到的code相当于临时生成的校验码, 而不是openid, 我们需要这个code, 结合开发者的appid和secret, 才可以拿到openid

    # minihalu
    appid =  'wx074832c83f06e898'
    secret = '93d3f5af8cd21cd8078805e00463d132'
    redirect_url = 'http://webapp.handu.com/vote'

    # 当session中没有openid时，则为非登录状态
    if session[:weixin_openid].blank?
      code = params[:code]
      if code.blank?
        redirect_to "https://open.weixin.qq.com/connect/oauth2/authorize?appid=#{appid}&redirect_uri=#{redirect_url}&response_type=code&scope=snsapi_base&state=0#wechat_redirect"
      else
        begin
          url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=#{appid}&secret=#{secret}&code=#{code}&grant_type=authorization_code"
           openid= JSON.parse(URI.parse(url).read)["openid"]
          if openid.present?
            session[:weixin_openid] = openid
          else
            return render plain: 500, status: 500
          end
        rescue Exception => e
          return render plain: 500, status: 500
        end
      end
    end
  end
end
