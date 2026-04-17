pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'diya1sicily/food-delivery-demo'
        DOCKER_CRED_ID = 'docker-hub-cred' 
    }

    stages {
        stage('Build & Test') {
            steps {
                // Must use bat for Windows
                bat 'mvn clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_CRED_ID, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        bat "docker login -u %USER% -p %PASS%"
                        bat "docker build -t ${DOCKER_IMAGE}:%BUILD_NUMBER% -t ${DOCKER_IMAGE}:latest ."
                        bat "docker push ${DOCKER_IMAGE}:%BUILD_NUMBER%"
                        bat "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
               bat "kubectl set image deployment/food-delivery-demo food-delivery-demo=diyasicily/food-delivery-demo:latest"
                bat "kubectl rollout status deployment/food-delivery-demo"
            }
        }
    }
}