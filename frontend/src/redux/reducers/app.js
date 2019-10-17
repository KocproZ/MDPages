import {RENAME_TITLE} from "../actionTypes";

const initialState = {
    title: "MDPages"
};

export default function (state = initialState, action) {
    switch (action.type) {
        case RENAME_TITLE: {
            document.title = action.title;
            return {
                title: action.title
            }
        }
        default: {
            return state;
        }
    }
}
