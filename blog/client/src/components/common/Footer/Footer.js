import React from "react";
import styles from "./Footer.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

const Footer = () => {
  return (
    <footer className={cx("footer")}>
      <div className={cx("content")}>
        &copy; Copyright 2020 - Marcin Kowalczyk
      </div>
    </footer>
  );
};

export default Footer;
