# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20160405092527) do

  create_table "lotteries", force: :cascade do |t|
    t.integer  "lottery_key"
    t.string   "lottery_name"
    t.integer  "lottery_num"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
  end

  create_table "records", force: :cascade do |t|
    t.string   "openid"
    t.string   "date"
    t.string   "vote_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "redeems", force: :cascade do |t|
    t.string   "redeem_code"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
  end

  create_table "users", force: :cascade do |t|
    t.string   "name"
    t.string   "sex"
    t.string   "phone",           limit: 11
    t.string   "address"
    t.string   "first_img_name"
    t.string   "second_img_name"
    t.string   "third_img_name"
    t.string   "slogan"
    t.integer  "vote_num"
    t.datetime "created_at",                 null: false
    t.datetime "updated_at",                 null: false
  end

  create_table "voters", force: :cascade do |t|
    t.string   "openid"
    t.integer  "lottery_times", default: 0
    t.string   "vote_id"
    t.string   "award"
    t.date     "current_date"
    t.datetime "created_at",                null: false
    t.datetime "updated_at",                null: false
  end

  add_index "voters", ["openid"], name: "index_voters_on_openid", unique: true

end
