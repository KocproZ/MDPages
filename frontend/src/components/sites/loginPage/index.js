import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {renameTitle} from "../../../redux/actions";

class LoginPage extends Component {
    componentDidMount() {
        this.props.dispatch(renameTitle("MDPages | Login"));
    }

    render() {
        return (
            <React.Fragment>
                <div className={"row loginPage"}>
                    <div className={"col-12 col-lg-5 login"}>
                        <form className={"row"}>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"login"} className={"col-12"}>Username or Email</label>
                                <input id={"login"} type={"text"} name={"login"} className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"password"} className={"col-12"}>Password</label>
                                <input id={"password"} type={"password"} name={"password"} className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <input type={"submit"} name={"submit"} className={"col-12"} value={"Login"}/>
                            </div>
                        </form>
                    </div>
                    <div className={"col-12 col-lg-2 divider"}>
                        <div>OR</div>
                    </div>
                    <div className={"col-12 col-lg-5 register"}>
                        <form className={"row"}>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"login"} className={"col-12"}>Username</label>
                                <input id={"login"} type={"text"} name={"login"} className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"register_email"} className={"col-12"}>Email</label>
                                <input id={"register_email"} type={"email"} name={"email"} className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"register_password"} className={"col-12"}>Password</label>
                                <input id={"register_password"} type={"password"} name={"password"} className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <label htmlFor={"register_repeat_password"} className={"col-12"}>Repeat Password</label>
                                <input id={"register_repeat_password"} type={"password"} name={"repeat_password"}
                                       className={"col-12"}
                                       required={true}/>
                            </div>
                            <div className={"row col-12 form-group"}>
                                <input type={"submit"} name={"submit"} className={"col-12"} value={"Register"}/>
                            </div>
                        </form>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default connect()(LoginPage);
