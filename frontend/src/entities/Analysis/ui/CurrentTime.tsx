function CurrentTime() {
  const date = new Date();

  return (
    <div>
      <h3>시간</h3>
      <p>{`${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`}</p>
      <p>{`${date.getHours()}:${date.getMinutes()}`}</p>
    </div>
  );
}

export default CurrentTime;
