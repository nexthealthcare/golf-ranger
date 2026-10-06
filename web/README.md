# 골프레인저 (Golf Ranger) 웹 버전 배포 가이드

본 디렉토리(`web/`)는 Cloudflare Pages, GitHub Pages, Vercel 등 정적 웹 호스팅 서비스에 즉시 배포할 수 있도록 제작된 독립형 웹 애플리케이션입니다.
2026년형 프리미엄 스포츠 퍼포먼스 랩(Apple Health, Garmin, WHOOP 스타일)의 절제되고 정제된 디자인 시스템이 적용되어 있습니다.

---

## 🚀 Cloudflare Pages를 통한 도메인 연결 3단계

### 1단계: GitHub 저장소에 푸시
```bash
git add .
git commit -m "Update Golf Ranger with premium athletic design"
git push origin main
```

### 2단계: Cloudflare Pages 연결 설정
1. [Cloudflare 대시보드](https://dash.cloudflare.com/)에 로그인합니다.
2. **Workers & Pages** 메뉴에서 **Create application** → **Pages** → **Connect to Git**을 선택합니다.
3. 깃허브 저장소(`GolfRanger`)를 선택합니다.
4. 빌드 설정(Build Settings)에서:
   - **Framework preset**: `None`
   - **Root directory**: `web` (또는 프로젝트 루트 배포 시 비워둠)
   - **Build command**: (비워둠)
   - **Build output directory**: (비워둠 또는 `.`)
5. **Save and Deploy**를 누릅니다. (`https://*.pages.dev` 도메인으로 수초 내 배포 완료)

### 3단계: 커스텀 도메인(Custom Domain) 안착
1. 배포 완료 후 Pages 프로젝트의 **Custom domains** 탭으로 이동합니다.
2. 보유하신 도메인(예: `golfranger.com` 또는 서브도메인)을 입력합니다.
3. Cloudflare DNS에 자동으로 CNAME 레코드가 연결되며, SSL(HTTPS) 인증서가 자동 발급되어 도메인으로 안착됩니다.

---

## 📱 웹 버전 주요 기능
- **골퍼 신체 기능 분석 분류**: 13항목 검진 데이터에 기반한 생체역학적 기능 결손 분류 (TYPE-A 얼리 익스텐션, TYPE-B 오버 더 탑 등)
- **13대 신체검진 프로토콜**: 키네틱 벡터 스키매틱 및 Web Speech API 기반 한국어 전문 음성 안내
- **원인 기여도 3분할 모델**: 신체(Body) %, 스윙(Swing) %, 게임(Game) %
- **바디-스윙-게임 인과관계 메커니즘**: 신체 결손이 실전 스윙 보상 및 타수 누수로 전이되는 기전 분석
- **맞춤 모빌리티 & 실전 드릴 처방**: 좌우 세트 가이드 및 음성 안내가 포함된 세트 완료 트래커
