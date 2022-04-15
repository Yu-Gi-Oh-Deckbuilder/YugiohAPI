import { User } from "../../../model/user.model";

export interface State {
  isAuthenticated: boolean;
  token: string | null;
  user: User | null;
  errorMessage: string | null;
}

export const initialState: State = {
  isAuthenticated: false,
  token: null,
  user: null,
  errorMessage: null,
}
