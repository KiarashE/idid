export PATH="$HOME/.jdk/bin:$HOME/.startup:$PATH"
export JAVA_HOME="\$HOME/.jdk"
limit=$(ulimit -u)
case $limit in
256)   # 1X Dyno
  default_java_opts="-Xmx384m -Xss512k"
;;
512)   # 2X Dyno
  default_java_opts="-Xmx768m"
;;
32768) # PX Dyno
  default_java_opts="-Xmx4g"
;;
*)
  default_java_opts="-Xmx384m -Xss512k"
;;
esac
export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:-"${default_java_opts} -Dfile.encoding=UTF-8 -Djava.rmi.server.useCodebaseOnly=true"}"
