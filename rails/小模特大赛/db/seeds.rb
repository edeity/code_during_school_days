# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)


# rake db:seed

# 所有奖励列表
Lottery.delete_all if  Lottery.count != 0
Lottery.create(lottery_key: 1, lottery_name: "MZ 10元无门槛优惠券", lottery_num: 2500)
Lottery.create(lottery_key: 2, lottery_name: "MZ 满399减100优惠券", lottery_num: 25000)
Lottery.create(lottery_key: 3, lottery_name: "HP 10元无门槛优惠券", lottery_num: 2500)
Lottery.create(lottery_key: 4, lottery_name: "果芽 10元无门槛优惠券", lottery_num: 2500)
Lottery.create(lottery_key: 5, lottery_name: "时尚童装", lottery_num: 0)
Lottery.create(lottery_key: 6, lottery_name: "嘉华旅游 200-20抵用券", lottery_num: 10000)
Lottery.create(lottery_key: 7, lottery_name: "嘉华旅游 4999-100抵用券", lottery_num: 400)

# 暴露给测试的接口

#Voter.create(openid: 'test', lottery_times: 100000, current_date: Date.current)