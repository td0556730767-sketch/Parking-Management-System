import {
  FETCH_DASHBOARD_START,
  FETCH_DASHBOARD_SUCCESS,
  FETCH_DASHBOARD_ERROR,
} from "./dashboardActionTypes";

const initialState = {
  data: null,
  loading: false,
  error: null,
};

export default function dashboardReducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_DASHBOARD_START:
      return { ...state, loading: true, error: null };

    case FETCH_DASHBOARD_SUCCESS:
      return { ...state, loading: false, data: action.payload };

    case FETCH_DASHBOARD_ERROR:
      return { ...state, loading: false, error: action.payload };

    default:
      return state;
  }
}