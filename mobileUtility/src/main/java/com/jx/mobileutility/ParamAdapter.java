package com.jx.mobileutility;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ParamAdapter extends BaseAdapter {
	private List<ParamDefine> params;
	private Context context = null;
	private LayoutInflater inflater=null;
	public ParamAdapter(List<ParamDefine> params, Context context) {
		this.params=params;
		this.context=context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return params.size();
	}

	@Override
	public Object getItem(int position) {
		return params.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public List<ParamDefine> getParams(){
		return params;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ParamDefine param=params.get(position);	
		
		if(param!=null){
			if("SWITCH".equals(param.getComponentType()) || "ALARM".equals(param.getComponentType())){
				convertView=inflater.inflate(R.layout.layout_radio, null);
				TextView txtParamName=(TextView)convertView.findViewById(R.id.txtParamName1);
				RadioGroup group=(RadioGroup)convertView.findViewById(R.id.grpParamValue1);
				final RadioButton rdoOn=(RadioButton)convertView.findViewById(R.id.rdoOption1);
				final RadioButton rdoOff=(RadioButton)convertView.findViewById(R.id.rdoOption2);
				rdoOn.setText(param.get(1));
				rdoOff.setText(param.get(0));
				txtParamName.setText(param.getParamName());
				if("1".equals(param.getValue())){
					rdoOn.setChecked(true);
				}
				if("0".equals(param.getValue())){
					rdoOff.setChecked(true);
				}
				if(!param.isCanWrite()){
					rdoOn.setEnabled(false);
					rdoOff.setEnabled(false);	
				}
				group.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if(checkedId==rdoOn.getId()){
							params.get(position).setValue("1");
						}else{
							params.get(position).setValue("0");
						}
					}
				});
				
			}else if("TEXT".equals(param.getComponentType())){
				convertView=inflater.inflate(R.layout.layout_text, null);
				TextView txtParamName=(TextView)convertView.findViewById(R.id.txtParamName);
				final EditText edtView= (EditText)convertView.findViewById(R.id.edtParamValue);
				txtParamName.setText(param.getParamName());
				edtView.setText(param.getValue());
				if(!param.isCanWrite()){
					edtView.setEnabled(false);
				}
				edtView.setFocusable(true);
				edtView.setFocusableInTouchMode(true);
				if("float".equals(param.getValueType())){
					edtView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				}
				edtView.setTag(position);
				edtView.setOnFocusChangeListener(new OnFocusChangeListener(){
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus){
							v.setBackgroundResource(R.drawable.editsharp_focus);
						}else{
							v.setBackgroundResource(R.drawable.editsharp);
						}
					}
				});
				edtView.addTextChangedListener(new TextWatcher(){
					
					@Override
					public void afterTextChanged(Editable s) {
						int maxLen=0;
						String tempStr=s.toString();
						ParamDefine param=params.get(position);
						if(param.getLength()==1){
							maxLen=3;
						}else if(param.getLength()==2){
							maxLen=5;
						}
						else if (param.getLength()==4){
							maxLen=9;
						}
						if(maxLen!=0 && s.toString().length()>maxLen){
							s.delete(s.length()-2, s.length()-1);
						}
						
						
						int pos=tempStr.toString().indexOf(".");
						if(pos>=0 && tempStr.toString().length()>pos+2){
							tempStr=tempStr.substring(0,pos+1)+tempStr.substring(pos+2);
							s.delete(pos+2, pos+3);
						}
						params.get(position).setValue(tempStr.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					
					}
					

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						
					}
					
				} );
			}else if("ALARM".equals(param.getComponentType())){
				convertView=inflater.inflate(R.layout.layout_radio, null);
				TextView txtParamName=(TextView)convertView.findViewById(R.id.txtParamName1);
				RadioButton rdoOn=(RadioButton)convertView.findViewById(R.id.rdoOption1);
				RadioButton rdoOff=(RadioButton)convertView.findViewById(R.id.rdoOption2);
				if(!param.isCanWrite()){
					rdoOn.setEnabled(false);
					rdoOff.setEnabled(false);
				}
				rdoOn.setText(param.get(1));
				rdoOff.setText(param.get(0));
				txtParamName.setText(param.getParamName());
			}
			else if("SBOX".equals(param.getComponentType())){
				convertView=inflater.inflate(R.layout.layout_radioext, null);
				TextView txtParamName=(TextView)convertView.findViewById(R.id.txtParamName2);
				RadioGroup group=(RadioGroup)convertView.findViewById(R.id.grpParamValue2);
				final RadioButton rdo1=(RadioButton)convertView.findViewById(R.id.rdoOption01);
				final RadioButton rdo2=(RadioButton)convertView.findViewById(R.id.rdoOption02);
				final RadioButton rdo3=(RadioButton)convertView.findViewById(R.id.rdoOption03);
				final RadioButton rdo4=(RadioButton)convertView.findViewById(R.id.rdoOption04);
				rdo1.setText(param.get(1));
				rdo2.setText(param.get(2));
				rdo3.setText(param.get(3));
				rdo4.setText(param.get(4));
				txtParamName.setText(param.getParamName());
				if("1".equals(param.getValue())){
					rdo1.setChecked(true);
				}
				if("2".equals(param.getValue())){
					rdo2.setChecked(true);
				}
				if("3".equals(param.getValue())){
					rdo3.setChecked(true);
				}
				if("4".equals(param.getValue())){
					rdo4.setChecked(true);
				}
				if(!param.isCanWrite()){
					rdo1.setEnabled(false);
					rdo2.setEnabled(false);	
					rdo3.setEnabled(false);
					rdo4.setEnabled(false);	
				}
				group.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if(checkedId==rdo1.getId()){
							params.get(position).setValue("1");
						}else if(checkedId==rdo2.getId()){
							params.get(position).setValue("2");
						}else if(checkedId==rdo3.getId()){
							params.get(position).setValue("3");
						}
						else{
							params.get(position).setValue("4");
						}
					}
				});
			}
		}
		return convertView;
	}

}
