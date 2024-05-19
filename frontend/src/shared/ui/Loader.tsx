import classes from './Loader.module.css';
function Spinner() {
  return (
    <div className={classes.container}>
      <div className={classes['dots-container']}>
        <div className={classes.dot}></div>
        <div className={classes.dot}></div>
        <div className={classes.dot}></div>
        <div className={classes.dot}></div>
      </div>
    </div>
  );
}
export function Loader() {
  return (
    <div className={classes.wrapper}>
      <img src="/img/logo.jpg" width={512} />
      <Spinner />
    </div>
  );
}
