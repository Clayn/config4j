node {
    def mvnHome
    def jdk = tool name: 'JDK 1.8'
    env.JAVA_HOME = "${jdk}"
    echo "Building "+env.BRANCH_NAME
    stage('Preparation') {
        checkout scm
        mvnHome = tool 'Maven'
        dir('Config4J') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' clean"
            } else {
                bat(/"${mvnHome}\bin\mvn" clean/)
            }
        }
        dir('Config4J-Spring') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' clean"
            } else {
                bat(/"${mvnHome}\bin\mvn" clean/)
            }
        }
    }
    dir('Config4J') {
        stage('Build') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests compile"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests compile/)
            }
        }
        stage('Test') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore=true test"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore=true test/)
            }
        }
        stage('Install') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests install"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests install/)
            }
        }
        stage('Results') {
            junit allowEmptyResults: true, testResults: '**/TEST-*.xml'
            jacoco()
        }
        stage('Report') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests site"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests site/)
            }
        }
        stage('Deploy') {
                    if(env.BRANCH_NAME.startsWith("v")) {
                            echo 'Deploying only because this commit is tagged...'
                            if (isUnix()) {
                                            sh "'${mvnHome}/bin/mvn' -DskipTests deploy -P github"
                                        } else {
                                            bat(/"${mvnHome}\bin\mvn" -DskipTests deploy -P github/)
                                        }

                    }
                }
    }

    dir('Config4J-Spring') {
        stage('Build') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests compile"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests compile/)
            }
        }
        stage('Test') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore=true test"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore=true test/)
            }
        }
        stage('Install') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests install"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests install/)
            }
        }
        stage('Results') {
            junit allowEmptyResults: true, testResults: '**/TEST-*.xml'
            jacoco()
        }
        stage('Report') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests site"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests site/)
            }
        }
        stage('Deploy') {
            if(env.BRANCH_NAME.startsWith("v")) {
                echo 'Deploying only because this commit is tagged...'
                if (isUnix()) {
                    sh "'${mvnHome}/bin/mvn' -DskipTests deploy -P github"
                } else {
                    bat(/"${mvnHome}\bin\mvn" -DskipTests deploy -P github/)
                }

            }
        }
    }
}