import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {renameTitle} from "../../../redux/actions";

class LoginPage extends Component {
    componentDidMount() {
        this.props.dispatch(renameTitle("MDPages | Login"));
    }

    render() {

        const FormGroup = (props) => {
            if (props.label)
                return <div className={"row col-12 form-group"}>
                    <label htmlFor={props.name} className={"col-12"}>{props.label}</label>
                    <input id={props.name} type={props.type} name={props.name} className={"col-12"}
                           required={props.required}/>
                </div>;
            return <div className={"row col-12 form-group"}>
                <input id={props.name} type={props.type} name={props.name} className={"col-12"}
                       required={props.required} value={props.value}/>
            </div>;
        };

        return (
            <React.Fragment>
                <div className={"row loginPage"}>
                    <div className={"col-12 col-lg-5 login"}>
                        <form className={"row"}>
                            <FormGroup name={"login_login"} type={"text"} label={"Username or Email"} required={true}/>
                            <FormGroup name={"login_password"} type={"password"} label={"Password"} required={true}/>
                            <FormGroup name={"login_submit"} type={"submit"} required={true} value={"Login"}/>
                        </form>
                    </div>
                    <div className={"col-12 col-lg-2 divider"}>
                        <div>OR</div>
                    </div>
                    <div className={"col-12 col-lg-5 register"}>
                        <form className={"row"}>
                            <FormGroup name={"register_login"} type={"text"} label={"Username"} required={true}/>
                            <FormGroup name={"register_email"} type={"email"} label={"Email"} required={true}/>
                            <FormGroup name={"register_password"} type={"password"} label={"Password"} required={true}/>
                            <FormGroup name={"register_repeat_password"} type={"email"} label={"Repeat Password"} required={true}/>
                            <FormGroup name={"register_submit"} type={"submit"} required={true} value={"Register"}/>
                        </form>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default connect()(LoginPage);
