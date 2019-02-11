class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :name # model的名字
      t.string :sex # model的性别
      t.string :phone, limit:11 # model的电话号码
      t.string :address # model的地址
      t.string :first_img_name # model的第一张图片
      t.string :second_img_name # model的第二张图片
      t.string :third_img_name # model的第三张图片
      t.string :slogan # 宣言
      t.integer :vote_num #投票数

      t.timestamps null: false
    end
  end
end
