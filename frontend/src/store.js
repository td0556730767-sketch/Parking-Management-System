import { createStore, combineReducers, applyMiddleware } from "redux";
import { thunk } from "redux-thunk";
import dashboardReducer from "./features/dashboard/dashboardSlice";
// import userReducer from "./features/users/userSlice";

const rootReducer = combineReducers({
  dashboard: dashboardReducer,
  // user: userReducer,
});

const store = createStore(rootReducer, applyMiddleware(thunk));

export default store;