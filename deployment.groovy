node("master"){
        stage("Build Image") {
                sh "cd testjen/src && git pull && docker build  -t nginxfrontend:$BUILD_NUMBER ."
                
        }
        stage("Deploy to DEV"){
                sh "docker rm -f nginxfrontend"
                sh "docker run -d --name nginxfrontend -p 80:80 nginxfrontend:$BUILD_NUMBER"
        }
}
