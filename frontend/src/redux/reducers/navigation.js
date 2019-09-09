import {TOGGLE_NAVIGATION} from "../actionTypes";

const initialState = {
    visibility: true
};

export default function (state = initialState, action) {
    switch (action.type) {
        case TOGGLE_NAVIGATION: {
            return {
                visibility: !state.visibility
            }
        }
        default: {
            return state;
        }
    }
}
