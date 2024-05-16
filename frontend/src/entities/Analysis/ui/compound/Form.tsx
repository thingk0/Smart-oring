import { ElementType, createContext, useContext } from 'react';
import { useState } from 'react';
import { ButtonOwnProps } from '@mui/material/Button/Button.ts';
import { TextFieldVariants } from '@mui/material/TextField/TextField.ts';
import {
  Button,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
  TypographyOwnProps,
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import dayjs from 'dayjs';
import styles from '../Analysis.module.css';
import axios from 'axios';
import { MissionObject } from '@entity/Analysis/store/useMissionStore';

type FormContext = {
  querys: object;
  setQuerys: React.Dispatch<React.SetStateAction<object>>;
  URL: string;
};

const FormContext = createContext<FormContext>({
  querys: {},
  setQuerys: () => <></>,
  URL: '',
});

type FormProps = {
  URL: string;
  children: JSX.Element | JSX.Element[];
};

type ChildrenProps = {
  children: JSX.Element | JSX.Element[];
};

function Form({ URL, children }: FormProps) {
  const [querys, setQuerys] = useState<object>({});

  return (
    <>
      <FormContext.Provider
        value={{
          URL,
          querys,
          setQuerys,
        }}
      >
        <form className={styles.flex_center}>{children}</form>
      </FormContext.Provider>
    </>
  );
}

interface TypographyProps extends TypographyOwnProps {
  component: ElementType;
}

function Title({ variant, component, children }: TypographyProps) {
  return (
    <Typography variant={variant} component={component}>
      {children}
    </Typography>
  );
}

type LabelProps = {
  id: string;
  children: string | JSX.Element | JSX.Element[];
};

function Label({ id, children }: LabelProps) {
  return <InputLabel id={id}>{children}</InputLabel>;
}

type SelectProps = {
  children: JSX.Element | JSX.Element[];
  id: string;
  label: string;
  queryParam: string;
};

function SelectC({ children, id, label, queryParam }: SelectProps) {
  const { querys } = useContext(FormContext);

  return (
    <>
      <Select labelId={id} label={label} value={querys[queryParam]}>
        {children}
      </Select>
    </>
  );
}

type OptionProps = {
  key: number;
  valueName: string;
  children: string | JSX.Element | JSX.Element[];
  queryParam: string;
};

function Option({ key, valueName, children, queryParam }: OptionProps) {
  const { querys, setQuerys } = useContext(FormContext);

  return (
    <MenuItem
      key={key}
      value={valueName}
      onClick={(e: any) => {
        setQuerys({
          ...querys,
          [queryParam]: e.target.innerText,
        });
      }}
    >
      {children}
    </MenuItem>
  );
}

function DatepickerProvider({ children }: ChildrenProps) {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      {children}
    </LocalizationProvider>
  );
}

type DatepickerProps = {
  defaultValue?: dayjs.Dayjs;
  maxDate?: dayjs.Dayjs;
  queryParam: string;
};

function Datepicker({ defaultValue, maxDate, queryParam }: DatepickerProps) {
  const { querys, setQuerys } = useContext(FormContext);
  return (
    <DatePicker
      defaultValue={defaultValue}
      maxDate={maxDate}
      value={querys[queryParam]}
      onChange={(value: object) => {
        setQuerys({ ...querys, [queryParam]: value });
      }}
    />
  );
}

type TextFieldProps = {
  type: string;
  defaultValue: string;
  variant: TextFieldVariants;
  queryParam: string;
};

function TextFieldC({
  type,
  defaultValue,
  variant,
  queryParam,
}: TextFieldProps) {
  const { querys, setQuerys } = useContext(FormContext);

  return (
    <TextField
      type={type}
      defaultValue={defaultValue}
      variant={variant}
      value={querys[queryParam]}
      onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
        setQuerys({ ...querys, [queryParam]: e.target.value })
      }
    />
  );
}

const ChangeQueryParams = (querys: object) => {
  let queryParam = '?';

  for (const [k, v] of Object.entries(querys)) {
    if (k.includes('Time')) {
      const tmp = new Date(v.$d);
      queryParam += `${k}=${tmp.toISOString()}&`;
    } else if (v !== 'ALL') {
      queryParam += `${k}=${v}&`;
    }
  }

  return queryParam;
};

interface ButtonProps extends ButtonOwnProps {
  url: string;
  setState: React.Dispatch<React.SetStateAction<Array<MissionObject>>>;
}

function ButtonC({ variant, url, setState }: ButtonProps) {
  const { querys } = useContext(FormContext);

  const onClickHandler = () => {
    axios
      .get(url + ChangeQueryParams(querys))
      .then(res => setState(res.data.content));
  };

  return (
    <Button variant={variant} onClick={onClickHandler}>
      검색
    </Button>
  );
}

Form.Title = Title;
Form.Label = Label;
Form.Select = SelectC;
Form.Option = Option;
Form.DatepickerProvider = DatepickerProvider;
Form.Datepicker = Datepicker;
Form.TextField = TextFieldC;
Form.Button = ButtonC;

export default Form;
