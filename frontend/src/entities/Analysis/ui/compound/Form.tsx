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
        {/* <FormControl fullWidth>{children}</FormControl> */}
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

/**
 * @deprecated [Bug] onChange가 발생하지 않음.
 */
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
  val: string;
  children: string;
  queryParam: string;
};

/**
 * @deprecated [Bug] onChange가 발생하지 않음.
 */
function Option({ key, children, val, queryParam }: OptionProps) {
  const { querys, setQuerys } = useContext(FormContext);

  console.log('option: ', val);

  return (
    <MenuItem
      key={key}
      value={val}
      // onClick={(e: any) => {
      //   console.log(e.target.innerText);
      //   setQuerys({
      //     ...querys,
      //     [queryParam]: e.target.innerText,
      //   });
      // }}
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

/**
 * @warning 잠재적인 유용성 문제로 인해 텍스트 필드에 type="number"를 사용하지 않는 것이 좋습니다.
 * 숫자가 아닌 특정 문자('e', '+', '-', '.')를 허용하고 다른 문자는 자동으로 삭제합니다.
 * 숫자를 증가/감소시키는 스크롤 기능으로 인해 실수로 눈에 띄기 어려운 변경이 발생할 수 있습니다.
 * 기타 – <input type="number">의 제한 사항에 대한 자세한 설명은 GOV.UK 디자인 시스템 팀이 숫자 입력 유형을 변경한 이유를 참조하세요.
 */

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
      // minLength={0}
    />
  );
}

const ChangeQueryParams = (querys: object, selectValue: string) => {
  let queryParam = '';

  if (selectValue && selectValue !== 'AMR00undefined')
    queryParam += `?amrType=${selectValue}`;
  else queryParam += '?';

  for (const [k, v] of Object.entries(querys)) {
    if (k.includes('Time')) {
      const tmp = new Date(v.$d);
      queryParam += `${k}=${tmp.toISOString()}&`;
    } else if (v !== 'ALL') {
      queryParam += `&${k}=${v}`;
    }
  }

  return queryParam;
};

interface ButtonProps extends ButtonOwnProps {
  setState: React.Dispatch<React.SetStateAction<Array<MissionObject>>>;
  selectValue: string;
}

function ButtonC({ variant, setState, selectValue }: ButtonProps) {
  const { querys, URL } = useContext(FormContext);

  const onClickHandler = () => {
    axios.get(URL + ChangeQueryParams(querys, selectValue)).then(res => {
      setState(res.data.resultData);
    });
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
