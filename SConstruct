#
# SConstruct for building the skycrates
# Java project.
#

from os import environ
env = Environment(  
    ENV             = environ,

    JAVA_VERSION    = "1.8.0",
    JAVA_HOME       = "/usr/lib/jvm/java-8-openjdk",
    JAVACLASSPATH   = ".:lib/spigot-1.8.8.jar"
)

classes = env.Java('target', 'src')

env.Jar(target = 'bin/skycrates.jar', source = [ classes, 'src/plugin.yml' ])
