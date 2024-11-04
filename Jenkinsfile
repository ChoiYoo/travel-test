pipeline {
    agent any

    environment {
        REGISTRY = 'ktb9/travel-server' // Docker Hub 레지스트리 이름
        IMAGE_TAG = "${env.BUILD_NUMBER}" // 이미지 태그는 빌드 번호로 설정
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials') // Docker Hub 자격증명
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ChoiYoo/travel-test.git'
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker build -t $REGISTRY:$IMAGE_TAG .
                        docker push $REGISTRY:$IMAGE_TAG
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
