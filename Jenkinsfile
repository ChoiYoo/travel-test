pipeline {
    agent any

    environment {
        REGISTRY = 'ktb9/travel-server' // Docker Hub 레지스트리 이름
        IMAGE_TAG = "${env.BUILD_NUMBER}" // 이미지 태그는 빌드 번호로 설정
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials') // Docker Hub 자격증명
        GIT_CREDENTIALS = credentials('travel-jenkins-prac') // GitHub 자격증명
        ARGOCD_USER = credentials('argocd-credentials').username // ArgoCD 자격증명
        ARGOCD_PASS = credentials('argocd-credentials').password
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

        stage('Update Helm Chart in Infra Branch') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'travel-jenkins-prac', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                        sh '''
                        git config --global user.email "green980611@naver.com"
                        git config --global user.name "ChoiYoo"
                        git checkout infra
                        sed -i "s/tag:.*/tag: ${IMAGE_TAG}/g" helm/values.yaml
                        git add helm/values.yaml
                        git commit -m "Update image tag to ${IMAGE_TAG}"
                        git push https://${GIT_USER}:${GIT_PASS}@github.com/ChoiYoo/travel-test.git infra
                        '''
                    }
                }
            }
        }

        stage('ArgoCD Sync') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'argocd-credentials', usernameVariable: 'ARGOCD_USER', passwordVariable: 'ARGOCD_PASS')]) {
                        sh '''
                        argocd login --username ${ARGOCD_USER} --password ${ARGOCD_PASS} --insecure
                        argocd app sync travel-app-prac
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
