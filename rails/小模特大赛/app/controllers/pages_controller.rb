# 负责相关逻辑的跳转
class PagesController < ApplicationController
  # skip_before_action :authenticate_openid!
  layout :false

  def signup
  end

  def vote
  end

  def details
  end

  def rules
  end

  def lottery
  end

end
