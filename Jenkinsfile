pipeline {
    agent any

    environment {
        // Change to your actual Docker Hub username
        DOCKER_IMAGE = 'diya1sicily/food-delivery-demo'
        // This credential ID must exist in Jenkins: Manage Jenkins -> Credentials
        DOCKER_CRED_ID = 'docker-hub-cred' 
    }

    stages {
        stage('Build & Test') {
            steps {
                // Use 'bat' for Windows
                bat 'mvn clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    // Login and build using batch commands
                    withCredentials([usernamePassword(credentialsId: DOCKER_CRED_ID, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        bat "docker login -u %USER% -p %PASS%"
                        // Using double quotes so Jenkins injects the build number
                        bat "docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} -t ${DOCKER_IMAGE}:latest ."
                        bat "docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                        bat "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // Runs kubectl directly. 
                // This assumes 'kubectl' is in your system PATH and configured locally
                bat "kubectl set image deployment/food-delivery-demo food-delivery-demo=${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
            }
        }
    }
}