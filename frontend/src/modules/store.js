import { combineReducers, configureStore } from "@reduxjs/toolkit";
import persistReducer from "redux-persist/es/persistReducer";
// import { setupListeners } from "@reduxjs/toolkit/dist/query";
import storage from "redux-persist/lib/storage";
import { roomApi } from "../services/room";
import { authApi } from "../services/auth";
import { userApi } from "../services/user";
import { studyApi } from "../services/study";
import userReducer from "./userReducer";
import studyReducer from "./studyReducer";
import farmReducer from "./farmReducer";
import { createBrowserHistory } from "history";
import { notificationApi } from "../services/notification";
import { userRoomApi } from "../services/userRoom";
import roomReducer from "./roomReducer";
import { farmApi } from "../services/farm";
import { rankingApi } from "../services/ranking";
import notificationReducer from "./notificationReducer";
export const history = createBrowserHistory();

const reducers = combineReducers({
  user: userReducer,
  study: studyReducer,
  room: roomReducer,
  farm: farmReducer,
  notification: notificationReducer,
  [roomApi.reducerPath]: roomApi.reducer,
  [authApi.reducerPath]: authApi.reducer,
  [userApi.reducerPath]: userApi.reducer,
  [studyApi.reducerPath]: studyApi.reducer,
  [userRoomApi.reducerPath]: userRoomApi.reducer,
  [notificationApi.reducerPath]: notificationApi.reducer,
  [farmApi.reducerPath]: farmApi.reducer,
  [rankingApi.reducerPath]: rankingApi.reducer,
});

const persistedReducer = persistReducer(
  {
    key: "root",
    storage,
  },
  reducers
);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }).concat([
      roomApi.middleware,
      authApi.middleware,
      userApi.middleware,
      userRoomApi.middleware,
      notificationApi.middleware,
      studyApi.middleware,
      farmApi.middleware,
      rankingApi.middleware,
    ]),
});

// setupListeners(store.dispatch);
