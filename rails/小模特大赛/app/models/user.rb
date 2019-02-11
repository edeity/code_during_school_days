class User < ActiveRecord::Base
  # presence: 非空
  validates :name, presence: true, length: {maximum: 10, too_long: "{content} 字符过多, 名字上限是10字"}
  validates :sex, presence: true, inclusion: {in: %w{男 女}, message: "%{value} 在性别项是不被接受的"}
  validates :phone, presence: true, format: {with: /0?1[3|4|5|8][0-9]\d{8}/, message: "电话号码不正确"}
  validates :address, presence: true, length: {maximum: 20, too_long: "{content} 字符串过多, 地址上限是20字"}
  validates :first_img_name, presence: true, length: {maximum: 100, too_long: "{content} 字符过多, 文件名应少于100字"}
  # second_img_data, third_img_data 可为空
  validates :slogan, presence: true, length: {maximum: 25, too_long: "%{content} 字符过多, 宣言上限是25字"}
  validates :vote_num, presence: true, numericality: {only_integer: true}
end
