worker_processes 5
timeout 15

app_root = File.expand_path("../..", __FILE__)
working_directory app_root

# Listen on fs socket for better performance
listen "/tmp/unicorn.cute_guy.sock", :backlog => 64
#listen 4098, :tcp_nopush => false

# Nuke workers after 30 seconds instead of 60 seconds (the default)
timeout 30

# App PID
pid "#{app_root}/tmp/pids/unicorn_cute_guy.pid"

# By default, the Unicorn logger will write to stderr.
# Additionally, some applications/frameworks log to stderr or stdout,
# so prevent them from going to /dev/null when daemonized here:
stderr_path "#{app_root}/log/unicorn.cute_guy.stderr.log"
stdout_path "#{app_root}/log/unicorn.cute_guy.stdout.log"

preload_app true

before_fork do |server, worker|
  Signal.trap 'TERM' do
    puts 'Unicorn master intercepting TERM and sending myself QUIT instead'
    Process.kill 'QUIT', Process.pid
  end

  defined?(ActiveRecord::Base) and
    ActiveRecord::Base.connection.disconnect!
end

after_fork do |server, worker|
  Signal.trap 'TERM' do
    puts 'Unicorn worker intercepting TERM and doing nothing. Wait for master to send QUIT'
  end

  defined?(ActiveRecord::Base) and
    ActiveRecord::Base.establish_connection
end