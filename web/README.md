# 골프레인저 (Golf Ranger) 웹 버전 배포 가이드

본 디렉토리(`web/`)는 Cloudflare Pages, GitHub Pages, Vercel 등 정적 웹 호스팅 서비스에 즉시 배포할 수 있도록 제작된 독립형 웹 애플리케이션입니다.

---

## 🚀 Cloudflare Pages를 통한 도메인 연결 3단계

### 1단계: GitHub 저장소에 푸시
```bash
git add .
git commit -m "Add Golf Ranger web version"
git push origin main
```

### 2단계: Cloudflare Pages 연결 설정
1. [Cloudflare 대시보드](https://dash.cloudflare.com/)에 로그인합니다.
2. **Workers & Pages** 메뉴에서 **Create application** → **Pages** → **Connect to Git**을 선택합니다.
3. 깃허브 저장소(`GolfRanger`)를 선택합니다.
4. 빌드 설정(Build Settings)에서:
   - **Framework preset**: `None`
   - **Root directory**: `web`
   - **Build command**: (비워둠)
   - **Build output directory**: (비워둠 또는 `.`)
5. **Save and Deploy**를 누릅니다. (수초 내에 무료 `https://*.pages.dev` 도메인으로 배포 완료)

### 3단계: 커스텀 도메인(Custom Domain) 안착
1. 배포 완료 후 Pages 프로젝트의 **Custom domains** 탭으로 이동합니다.
2. 보유하신 도메인(예: `golfranger.com` 또는 서브도메인 `app.golfranger.com`)을 입력합니다.
3. Cloudflare DNS에 자동으로 CNAME 레코드가 연결되며, SSL(HTTPS) 인증서가 자동 발급되어 도메인으로 안착됩니다.

---

## 📱 웹 버전 주요 기능
- **골프 바디 MBTI 진단**: 설문 및 신체검진에 따라 🐯 BSE-T 배치기 타이거형, 🐲 OSD-D 엎어치기 드래곤형 등 즉시 산출
- **13대 신체검진**: 인터랙티브 캔버스 애니메이션 및 Web Speech API 기반 한국어 음성 가이드
- **3분할 원인 기여도**: 바디(신체) %, 스윙(기술) %, 게임(클럽) %
- **맞춤 처방**: 하루 3분 모빌리티 스트레칭 및 연습장 원포인트 드릴
