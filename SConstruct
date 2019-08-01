#
# SConstruct for building the skycrates
# Java project.
#

from os import environ
env = Environment(  ENV             = environ,
                    JAVA_VERSION    = "1.8.0",
                    JAVA_HOME       = "/usr/lib/jvm/java-8-openjdk",
                    JAVACLASSPATH   = ".:bin/spigot-1.8.8.jar" )

classes = env.Java('target/', 'src/')
env.Jar('bin/skycrates.jar', classes)
