class Voter < ActiveRecord::Base
  # presence: 非空
  validates :openid, presence: true# uniqeness: true # 从微信获取的openid,用来唯一标识用户, 测试时该值可以相同
  validates :lottery_times, presence: true, numericality: {only_integer: true}

  def self.update_vote_everyday
    voters = Voter.all
    voters.each do |voter|
      voter.update(vote_times: 3, lottery_tiems: 0)
    end
  end
end