class CreateVoters < ActiveRecord::Migration
  def change
    create_table :voters do |t|
      t.string :openid # 用来表示用户的id, 该id是由微信产生的(对于一个公众号,该id唯一)
      t.integer :lottery_times, default: 0 # 可以抽奖的次数
      t.string :vote_id # 已经投票的id(今天不能再为该id投票)
      t.string :award # 奖品
      t.date :current_date # 当前日期
      t.timestamps null: false
    end
    add_index :voters, :openid, unique: true
  end
end
