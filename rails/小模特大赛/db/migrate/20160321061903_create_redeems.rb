class CreateRedeems < ActiveRecord::Migration
  def change
    create_table :redeems do |t|
      t.string :redeem_code # 生成的抽奖码
      t.timestamps null: false
    end
  end
end
