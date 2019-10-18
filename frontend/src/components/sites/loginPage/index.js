import React, {Component} from 'react';
import './style.scss';
import {connect} from "react-redux";
import {renameTitle} from "../../../redux/actions";
import FormGroup from "./FormGroup";

class LoginPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            login_login: "",
            login_password: "",
            register_login: "",
            register_email: "",
            register_password: "",
            register_repeat_password: ""
        };
        this.handleLogin = this.handleLogin.bind(this);
        this.handleRegister = this.handleRegister.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        this.props.dispatch(renameTitle("MDPages | Login"));
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    };

    handleLogin = (event) => {
        event.preventDefault();
    };

    handleRegister = (event) => {
        event.preventDefault();
    };

    render() {
        return (
            <React.Fragment>
                <div className={"row loginPage"}>
                    <div className={"col-12 col-lg-5 login"}>
                        <form className={"row"} onSubmit={this.handleLogin}>
                            <FormGroup name={"login_login"} type={"text"} label={"Username or Email"}
                                       value={this.state.login_login} required={true} handleChange={this.handleChange}/>
                            <FormGroup name={"login_password"} type={"password"} label={"Password"} required={true}
                                       handleChange={this.handleChange}/>
                            <FormGroup name={"login_submit"} type={"submit"} required={true} value={"Login"}/>
                        </form>
                    </div>
                    <div className={"col-12 col-lg-2 divider"}>
                        <div>OR</div>
                    </div>
                    <div className={"col-12 col-lg-5 register"}>
                        <form className={"row"} onSubmit={this.handleRegister}>
                            <FormGroup name={"register_login"} type={"text"} label={"Username"} required={true}
                                       handleChange={this.handleChange}/>
                            <FormGroup name={"register_email"} type={"email"} label={"Email"} required={true}
                                       handleChange={this.handleChange}/>
                            <FormGroup name={"register_password"} type={"password"} label={"Password"} required={true}
                                       handleChange={this.handleChange}/>
                            <FormGroup name={"register_repeat_password"} type={"password"} label={"Repeat Password"}
                                       required={true} handleChange={this.handleChange}/>
                            <FormGroup name={"register_submit"} type={"submit"} required={true} value={"Register"}/>
                        </form>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default connect()(LoginPage);
