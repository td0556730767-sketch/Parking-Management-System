import {
  FETCH_DASHBOARD_START,
  FETCH_DASHBOARD_SUCCESS,
  FETCH_DASHBOARD_ERROR,
} from "./dashboardActionTypes";
import { mockDashboardData } from "./mockDashboardData";

// action creators "רגילים" — סינכרוניים
export const fetchDashboardStart = () => ({ type: FETCH_DASHBOARD_START });

export const fetchDashboardSuccess = (data) => ({
  type: FETCH_DASHBOARD_SUCCESS,
  payload: data,
});

export const fetchDashboardError = (error) => ({
  type: FETCH_DASHBOARD_ERROR,
  payload: error,
});

// thunk — פונקציה שמחזירה פונקציה (דורש redux-thunk).
// זו הפונקציה היחידה שתשתנה כשיהיה שרת אמיתי:
// במקום setTimeout+mock, יהיה fetch(`/api/dashboard/${userId}`).
export const fetchDashboardData = (userId) => async (dispatch) => {
  dispatch(fetchDashboardStart());
  try {
    const data = await new Promise((resolve) =>
      setTimeout(() => resolve(mockDashboardData), 300)
    );
    dispatch(fetchDashboardSuccess(data));
  } catch (err) {
    dispatch(fetchDashboardError(err.message || "שגיאה בטעינת הנתונים"));
  }
};