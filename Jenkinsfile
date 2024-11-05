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
                git branch: 'infra-update', url: 'https://github.com/ChoiYoo/travel-test.git'
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

        stage('Update Helm Chart') {
            steps {
                script {
                    sh '''
                    sed -i 's/tag:.*/tag: ${IMAGE_TAG}/g' helm/values.yaml
                    git config --global user.email "green980611@naver.com"
                    git config --global user.name "ChoiYoo"
                    git add helm/values.yaml
                    git commit -m "Update image tag to ${IMAGE_TAG}"
                    git push origin infra
                    '''
                }
            }
        }

        stage('ArgoCD Sync') {
            steps {
                sh '''
                argocd app sync travel-app-prac
                '''
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}