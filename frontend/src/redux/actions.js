import {RENAME_TITLE, TOGGLE_NAVIGATION} from "./actionTypes";

export const toggleNavigation = () => ({
    type: TOGGLE_NAVIGATION,
});

export const renameTitle = (title) => ({
    type: RENAME_TITLE,
    title: title
});
