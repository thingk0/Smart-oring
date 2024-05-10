function Setting() {
  return (
    <main>
      <h1>환경설정</h1>
      <h2>그래픽</h2>
      <div>
        <h3>고급 설정</h3>
        <ul>
          <li>
            <div>렌더링 크기</div>
            <div>
              <input type="range" min={50} max={200} step={10} />
            </div>
          </li>
          <li>
            <div>시야 FOV</div>
          </li>
          <li>
            <div>전반적인 그래픽 품질</div>
          </li>
          <li>
            <div>안티앨리어싱</div>
          </li>
          <li>
            <div>그림자</div>
          </li>
          <li>
            <div>텍스처</div>
          </li>
          <li>
            <div>앰비언트 오클루전</div>
          </li>
        </ul>
      </div>
    </main>
  );
}
export default Setting;
