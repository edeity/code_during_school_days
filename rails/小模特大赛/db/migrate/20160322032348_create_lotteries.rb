class CreateLotteries < ActiveRecord::Migration
  def change
    create_table :lotteries do |t|
      t.integer :lottery_key
      t.string :lottery_name # 奖品的名称
      t.integer :lottery_num # 奖品的数量
      t.timestamps null: false
    end
  end
end
