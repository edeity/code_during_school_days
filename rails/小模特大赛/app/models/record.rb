class Record < ActiveRecord::Base
  validates :openid, presence: true # 投票者
  validates :date, presence: true # 日期
  validates :vote_id, presence: true # 被投票者
end
