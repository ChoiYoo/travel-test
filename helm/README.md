# Travel 프로젝트 Helm 차트

### 개요
이 Helm 차트는 `travel` 프로젝트를 Kubernetes 클러스터에 배포하기 위한 설정을 포함하고 있습니다. 차트는 `server`, `client`, `ai`의 세 가지 주요 서비스로 구성되어 있으며, 각 서비스는 개별 **Deployment**와 **Service**로 배포됩니다.

---

### 프로젝트 구조

```plaintext
travel/
├── Chart.yaml                # Helm 차트의 메타정보
├── templates/                # Kubernetes 리소스 템플릿 파일
│   ├── server-deployment.yaml
│   ├── server-service.yaml
│   ├── client-deployment.yaml
│   ├── client-service.yaml
│   ├── ai-deployment.yaml
│   └── ai-service.yaml
└── values.yaml               # 설정 파일
```

- **Chart.yaml**: Helm 차트의 이름, 버전, 설명 등의 메타데이터를 포함한 파일입니다.
- **templates/**: Kubernetes 리소스를 정의하는 템플릿 파일들이 위치하는 디렉토리입니다.
- **values.yaml**: 템플릿에 주입되는 기본 설정 값을 정의하는 파일입니다.

---


### 설치 및 배포

#### 1. Helm 설치
Helm이 설치되지 않은 경우 다음 명령어로 설치합니다:

```bash
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
```
-> 설치 관련 사이트 : https://helm.sh/ko/docs/intro/install/

#### 2. 템플릿 렌더링 확인
배포 전에 `helm template` 명령어로 템플릿이 올바르게 렌더링되는지 확인합니다. (Chart.yaml 파일 위치에서)

```bash
helm template travel .
```

#### 3. Helm 차트 배포
템플릿이 올바르면 차트를 다음과 같이 배포합니다:

```bash
helm install travel .
```

#### 4. 차트 업데이트
`values.yaml` 파일을 수정한 후 아래 명령어로 배포된 리소스를 업데이트할 수 있습니다:

```bash
helm upgrade travel .
```

---

### 관리 명령어

- **차트 삭제**: 차트를 삭제하려면 아래 명령어를 사용합니다.
  ```bash
  helm uninstall travel
  ```

- **배포 상태 확인**: 배포된 리소스의 상태를 확인하려면 아래 명령어를 사용합니다.
  ```bash
  kubectl get all
  ```

