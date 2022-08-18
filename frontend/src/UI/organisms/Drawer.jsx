import React, { useState } from "react";
import kakao_oauth from "../../assets/kakao_login_large_narrow.png";
import DdayWidget from "../molecules/DdayWidget";
import StudyWidget from "../molecules/StudyWidget";
import Button from "../atoms/Button";
import { useSelector } from "react-redux";
import { useLazyLogoutQuery } from "../../services/auth";
import { KAKAO_AUTH_URL } from "../../services";
import { Link, useNavigate } from "react-router-dom";
// import CalendarWidget from "../molecules/Calendar";
import Calendar from "react-calendar";
import "./Calendar.css";

function Drawer({ children }) {
  const isLogin = useSelector((state) => state.user.isLogin);
  const user = useSelector((state) => state.user.user);
  const [logout] = useLazyLogoutQuery();
  const navigate = useNavigate();

  const logoutHandler = () => {
    logout();
    navigate("/");
  };

  const loginHandler = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  const [value, onChange] = useState(new Date());
  return (
    <div className="text-font1 drawer drawer-end font-sans">
      <input id="my-drawer-4" type="checkbox" className="drawer-toggle" />
      <div className="drawer-content">{children}</div>
      <div className="drawer-side">
        <label htmlFor="my-drawer-4" className="drawer-overlay"></label>
        {/* 로그인한 사용자인 경우 */}
        {isLogin && (
          <div className="p-10 overflow-y-auto w-[380px] bg-bg rounded-tl-3xl rounded-bl-3xl">
            <div className="flex justify-between text-sub1">
              <i className="bx bxs-user-circle text-5xl"></i>
              <div className="text-font5 font-normal text-base mb-6">
                <span className="flex justify-end font-semibold">{`${user?.currentSley} 슬리`}</span>
                <span className="flex justify-end font-semibold">{`${user?.currentBookPoint} 포인트`}</span>
              </div>
            </div>

            <div className="text-font1 font-bold mt-3 mb-6">
              <div className="text-lg">
                <span className="text-lg">{user?.name} 님</span>
                <br />
                항상 잘 하고 있어요!
              </div>
            </div>
            <div className="mb-6 flex justify-start gap-3">
              <Link to="/me">
                <Button text={"마이페이지"} />
              </Link>
              <Button text={"로그아웃"} onClick={logoutHandler} />
            </div>

            {/* ========위젯========= */}
            <div className="flex flex-col gap-5">
              <DdayWidget />
              {/* <StudyWidget /> */}
              <div className="w-100 items-center justify-between p-6 shadow rounded-3xl">
                <Calendar onChange={onChange} value={value} locale="en-EN" />
              </div>
            </div>
          </div>
        )}
        {/* 로그인하지 않은 사용자인 경우 */}
        {!isLogin && (
          <div className="p-10 pt-24 overflow-y-auto w-[380px] bg-bg rounded-tl-3xl rounded-bl-3xl">
            <div className="text-center text-3xl font-bold text-font1 mb-6">
              로그인
            </div>
            <div className="text-center text-lg font-normal text-font5 mb-16">
              파슬리와 함께 공부하면서 <br />
              허브 도감을 채워보세요!
            </div>
            <div className="flex justify-center">
              <button className="w-48 h-12" onClick={loginHandler}>
                <img
                  src={kakao_oauth}
                  alt="카카오 로그인"
                  className="kakao object-fill"
                />
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Drawer;
