node("master"){
        stage("Build Image") {
                sh "cd testjen/src && git pull && docker build  -t nginxfrontend:$BUILD_NUMBER ."
                
        }
        stage("Deploy to DEV"){
                sh "docker rm -f nginxfrontend"
                sh "docker run -d --name nginxfrontend -p 80:80 nginxfrontend:$BUILD_NUMBER"
        }
       stage("Test the URL"){
                try{
                        def output = sh script: "curl http://marius-lb-491722137.eu-west-1.elb.amazonaws.com/", returnStdout: true
                        println "Output message is: "+output
                        if(output.contains("awesome")){
                                println "Ok i found it!!!!"
                        }else{
                                println "not found!!!!!! very bad image!!"
                                sh "exit 1"
                        }

                }catch(err){
                        println "error!!!"
                        sh "exit 1"
                }
        }
}
