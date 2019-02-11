require File.expand_path('../boot', __FILE__)

require 'rails/all'

Bundler.require(*Rails.groups)

module CuteGuyBackEnd
  class Application < Rails::Application
    #设置使用北京时间
    config.time_zone = 'Beijing'
    config.active_record.default_timezone = :local
    config.active_record.raise_in_transactional_callbacks = true
  end
end
