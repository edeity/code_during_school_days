class CreateRecords < ActiveRecord::Migration
  def change
    create_table :records do |t|
      t.string :openid # 投票者
      t.string :date # 投票日期
      t.string :vote_id # 被投票者
      t.timestamps null: false
    end
  end
end
