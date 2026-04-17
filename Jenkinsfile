pipeline {
    agent any

    environment {
        // Change to your actual Docker Hub username
        DOCKER_IMAGE = 'diya1sicily/food-delivery-demo'
        // These refer to Credentials IDs created in Jenkins (explained below)
        DOCKER_CRED_ID = 'docker-hub-creds' 
        K8S_CRED_ID = 'kubeconfig-file'
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls code from your GitHub repository
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Runs Maven build and tests
                sh 'mvn clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    // Logs in to Docker Hub and builds/pushes the image
                    docker.withRegistry("https://index.docker.io/v1/", DOCKER_CRED_ID) {
                        def appImage = docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                        appImage.push()
                        appImage.push("latest")
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // Uses the kubeconfig credential to talk to your cluster
                withCredentials([file(credentialsId: K8S_CRED_ID, variable: 'KUBECONFIG')]) {
                    sh "kubectl set image deployment/food-delivery-demo food-delivery-demo=${DOCKER_IMAGE}:${env.BUILD_NUMBER} --kubeconfig=$KUBECONFIG"
                }
            }
        }
    }
}