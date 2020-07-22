import React, { Component } from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Label,
  Alert,
  Row,
  Col,
} from "reactstrap";
import {
  AvForm,
  AvField,
  AvGroup,
  AvInput,
} from "availity-reactstrap-validation";
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from "../../constants";
import fbLogo from "../../images/fb-logo.png";
import googleLogo from "../../images/google-logo.png";
import styles from "./LoginModal.scss";
import classNames from "classnames/bind";
const cx = classNames.bind(styles);

class LoginModal extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loginError: false,
    };
  }

  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose, showModal } = this.props;

    return (
      <Modal isOpen={showModal} toggle={handleClose}>
        <AvForm onSubmit={this.handleSubmit}>
          <ModalHeader id="login-title" toggle={handleClose}>
            Sign In
          </ModalHeader>
          <ModalBody>
            <Row>
              <Col md="12">
                {loginError ? (
                  <Alert color="danger">
                    <strong>Failed to sign in!</strong> Please check your
                    credentials and try again.
                  </Alert>
                ) : null}
              </Col>
              <Col md="12">
                <AvField
                  name="username"
                  type="username"
                  label="Username"
                  placeholder="Your Username"
                  required
                  errorMessage="Username can not be empty!"
                />
                <AvField
                  name="password"
                  type="password"
                  label="Password"
                  placeholder="Your Password"
                  required
                  errorMessage="Password can not be empty!"
                />
                <AvGroup check inline>
                  <Label className="form-check-label">
                    <AvInput type="checkbox" name="rememberMe" /> Remember me
                  </Label>
                </AvGroup>
              </Col>
            </Row>
            <div className="mt-1">&nbsp;</div>
            <div className="login-container">
              <SocialLogin />
            </div>
          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={handleClose} tabIndex="1">
              Cancel
            </Button>{" "}
            <Button color="primary" type="submit">
              Sign In
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}

class SocialLogin extends Component {
  render() {
    return (
      <div className="social-login">
        <Button
          block
          color="link"
          className={cx("google")}
          href={GOOGLE_AUTH_URL}
        >
          <img src={googleLogo} alt="Google" /> Log in with Google
        </Button>
      </div>
    );
  }
}

export default LoginModal;
