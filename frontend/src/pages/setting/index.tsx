import {
  FormControl,
  Grid,
  Input,
  MenuItem,
  Select,
  SelectChangeEvent,
  Slider,
} from '@mui/material';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';
import { useState } from 'react';

export function SettingPage() {
  const {
    fov,
    actions: { setFov },
  } = useGraphicsQualityStore();
  const [value, setValue] = useState(30);

  const handleFOVSliderChange = (event: Event, newValue: number | number[]) => {
    setFov(newValue as number);
  };

  const handleFOVInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFov(event.target.value === '' ? 0 : Number(event.target.value));
  };
  const handleBlur = () => {
    if (value < 0) {
      setValue(0);
    } else if (value > 100) {
      setValue(100);
    }
  };
  const [quality, setQuality] = useState('');

  const handleChange = (event: SelectChangeEvent) => {
    setQuality(event.target.value);
  };
  return (
    <main>
      <h1>환경설정</h1>
      <h2>그래픽</h2>
      <div>
        <h3>고급 설정</h3>
        <ul>
          <li>
            <div>렌더링 크기</div>
            <div></div>
          </li>
          <li>
            <div>카메라 시야 FOV</div>
            <div>
              <Grid container spacing={2} alignItems="center">
                <Grid item xs>
                  <Slider
                    value={typeof fov === 'number' ? fov : 0}
                    onChange={handleFOVSliderChange}
                    aria-labelledby="input-slider"
                  />
                </Grid>
                <Grid item>
                  <Input
                    value={fov}
                    size="small"
                    onChange={handleFOVInputChange}
                    onBlur={handleBlur}
                    inputProps={{
                      step: 10,
                      min: 0,
                      max: 100,
                      type: 'number',
                      'aria-labelledby': 'input-slider',
                    }}
                  />
                </Grid>
              </Grid>
            </div>
          </li>
          <li>
            <div>전반적인 그래픽 품질</div>
            <div>
              <FormControl sx={{ m: 1, minWidth: 120 }}>
                <Select
                  value={quality}
                  onChange={handleChange}
                  displayEmpty
                  inputProps={{ 'aria-label': 'Without label' }}
                  renderValue={selected => {
                    if (selected.length === 0) {
                      return <em>Placeholder</em>;
                    }

                    return selected;
                  }}
                >
                  <MenuItem value={'low'}>낮음</MenuItem>
                  <MenuItem value={'medium'}>보통</MenuItem>
                  <MenuItem value={'high'}>높음</MenuItem>
                </Select>
              </FormControl>
            </div>
          </li>
          <li>
            <div>안티앨리어싱</div>
          </li>
          <li>
            <div>그림자</div>
          </li>
          <li>
            <div>텍스처</div>
          </li>
          <li>
            <div>앰비언트 오클루전</div>
          </li>
        </ul>
      </div>
    </main>
  );
}
