# 简介

针对米妮哈鲁第二届小模特大赛的活动页面， 基本功能

1. 提交表单
2. 展示小模特信息,并投票
3. 抽奖

## 前端：

### 引用第三方JS：

- jquery
- [rem.js](http://isux.tencent.com/web-app-rem.html)
- [识别手势的图片截取插件](https://github.com/baijunjie/jQuery-photoClip)
- [图片轮播插件: Swiper](https://github.com/nolimits4web/Swiper)

### 额外功能:

- 微信认证

之所以需要微信认证,是因为在上一届活动中,因不作限制,导致有人刷票,所以系统崩啦!

微信认证代码请参考app/controllers/ApplicationController.rb的authenticate_openid!

基本步骤

1. 在某公众号管理平台，绑定域名，如`webapp.handu.com`;
2. 每次进来前执行:rb的authenticate_openid!方法; rb的authenticate_openid!

