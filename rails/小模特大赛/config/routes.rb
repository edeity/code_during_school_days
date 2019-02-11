Rails.application.routes.draw do

  root 'pages#signup'
  # root 'pages#vote'

  # 路由, 以下操作相当于对应相应的html, 只不过中间多了一步微信认证的过程
  get 'signup', to: 'pages#signup'
  get 'vote', to: 'pages#vote'
  get 'rules', to: 'pages#rules'
  get 'lottery', to: 'pages#lottery'
  get 'details', to: 'pages#details'

  # 获取单个或多个模特信息的页面
  get 'user/:id', to:'users#each' # 获取 单个model的详细信息
  get 'profile', to: 'users#profile'

  # 报名
  post 'user', to: 'users#commit' # 提交表单
  post 'upload', to: 'users#upload' # 上传图片

  # 其他操作
  post 'vote/:id', to: 'voters#vote' # 为用户投票
  post 'lottery', to: 'lotteries#lottery' # 抽取奖品


  # 投票页面の后台管理
  get 'admin', to: 'admin#admin'
  get 'result', to: 'admin#result' # 抽奖路由
  get 'admin/each/:id', to: 'admin#each' # 查看参赛选手信息
  post 'admin/update/:id', to: 'admin#update' # 更改参赛者信息
  post 'admin/delete/:id', to: 'admin#delete' # 删除参赛者信息

end
