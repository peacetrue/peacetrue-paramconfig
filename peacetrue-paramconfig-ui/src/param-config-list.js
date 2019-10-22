let {Card, Form, FormItem, Input, Icon, Button, InputNumber, DatePicker} = require('iview/dist/iview');
let PageTable = require('peacetrue-iview/dist/components/page-table');
let Axios = require('axios');
let Lodash = require('lodash');
let PromiseConfirm = require('peacetrue-iview/dist/mixins/promise-confirm');
let {Rules, Generator, setFields} = require('peacetrue-async-validator');

module.exports = {
    name: 'ParamConfigList',
    template: `
    <div class="param-config-list">
        <Card v-show="mode=='page'">
            <span slot="title">查询条件</span>
            <Form ref="form" :model="params" inline>
                <FormItem prop="code">
                    <Input type="text" v-model="params.code" placeholder="编号"/>
                </FormItem>
                <FormItem prop="name">
                    <Input type="text" v-model="params.name" placeholder="名称"/>
                </FormItem>
                <FormItem prop="createdTime.lowerBound">
                    <DatePicker type="date" placeholder="创建起始时间" v-model="params.createdTime.lowerBound"></DatePicker>
                </FormItem>
                <FormItem prop="createdTime.upperBound">
                    <DatePicker type="date" placeholder="创建结束时间" v-model="params.createdTime.upperBound"></DatePicker>
                </FormItem>
                <FormItem >
                    <Button type="primary" @click="query(true)">查询</Button>
                    <Button @click="reset">清空</Button>
                </FormItem>
            </Form>
        </Card>
        <Row class="operation-bar" v-show="mode=='page'">
            <Col span="24">
                <Button type="primary" size="small" @click="openAdd()">新增</Button>
                &nbsp;
                <Button type="primary" size="small" @click="remove()">删除</Button>
            </Col>
        </Row>
        <Card>
            <div slot="title">查询结果</div>
            <page-table ref="pageTable" :url="url" :columns="columns" v-model="params" :success-format="successFormat"></page-table>
        </Card>
        <Modal v-model="detail.model" :title="detail.title">
            <Form ref="detail" :model="detail.data" :rules="detail.rules" :label-width="100">
                <FormItem label="编号" prop="code">
                    <i-input type="text" v-model="detail.data.code" placeholder="必填、长度1~31" :readonly="detail.readonly.code" :class="detail.style.code"></i-input>
                </FormItem>
                <FormItem label="名称" prop="name">
                    <i-input type="text" v-model="detail.data.name" placeholder="必填、长度1~255" :readonly="detail.readonly.name" :class="detail.style.name"></i-input>
                </FormItem>
                <FormItem label="值" prop="value">
                    <i-input type="text" v-model="detail.data.value" placeholder="必填、长度1~255" :readonly="detail.readonly.value" :class="detail.style.value"></i-input>
                </FormItem>
                <FormItem label="备注" prop="remark">
                    <i-input type="text" v-model="detail.data.remark" placeholder="选填、长度1~255" :readonly="detail.readonly.remark" :class="detail.style.remark"></i-input>
                </FormItem>
                <FormItem label="创建者" prop="creatorId" :class="detail.style.creatorId">
                    <i-input type="text" v-model="detail.data.creatorId" :readonly="detail.readonly.creatorId" :class="detail.style.creatorId"></i-input>
                </FormItem>
                <FormItem label="创建时间" prop="createdTime" :class="detail.style.createdTime">
                    <i-input type="text" v-model="detail.data.createdTime" :readonly="detail.readonly.createdTime" :class="detail.style.createdTime"></i-input>
                </FormItem>
            </Form>
            <div slot="footer" v-show="save">
                <i-button type="success" size="large" long :loading="detail.loading" @click="save">提交</i-button>
            </div>  
        </Modal>      
    </div>
    `,
    props: {
        mode: {type: String, required: false, default: 'page'}, // page | list
        url: {type: String, required: false, default: '/param-configs'},
        uniqueUrl: {type: String, required: false, default: '/common/param-configs/unique'},
        params: {type: Object, required: false, default() {return {page: 0, size: 10, code: null, name: null, createdTime: {lowerBound: null, upperBound: null}};}},
        successFormat: {type: Function, required: false, default(data) {return data;}},
        columns: {
            type: Array, required: false, default() {
                return [
                    {type: 'selection', width: 60, align: 'center'},
                    {title: '编号', key: 'code', width: 150, tooltip: true},
                    {title: '名称', key: 'name', width: 150, tooltip: true},
                    {title: '值', key: 'value', width: 200, tooltip: true},
                    {title: '备注', key: 'remark', width: 300, tooltip: true},
                    {title: '创建者', key: 'creatorId', width: 120},
                    {title: '创建时间', key: 'createdTime', width: 200, tooltip: true, sortable: 'custom', sortType: 'desc'},
                    {title: '修改者', key: 'modifierId', width: 130,},
                    {title: '修改时间', key: 'modifiedTime', width: 200},
                    {title: '操作', width: 200, fixed: 'right', render: (h, r) => {return this.renderOperate(h, r);}},
                ];
            }
        },
    },
    data() {
        setFields({code: "编号", name: "名称", value: "值", remark: "备注"});
        return {
            detail: {
                model: false,
                loading: false,
                data: {},
                readonly: {},
                style: {},
                rules: Generator.generate({
                    code: [{required: true}, {max: 31}, Rules.use('unique', {url: this.uniqueUrl})],
                    name: [{required: true}, {max: 255}],
                    value: [{required: true}, {max: 255}],
                    remark: [{max: 255}],
                }),
            },
        };
    },
    methods: {
        query(reset) {
            this.$refs.pageTable.query(reset);
        },
        renderOperate(h, r) {
            let buttons = [
                h(Button, {props: {type: 'primary', size: 'small'}, on: {click: () => this.openView(r.row)}}, '查看'),
                " ",
                h(Button, {props: {type: 'primary', size: 'small'}, on: {click: () => this.openModify(r.row)}}, '修改'),
                " ",
                h(Button, {props: {type: 'primary', size: 'small'}, on: {click: () => this.remove(r.row)}}, '删除'),
            ];
            return h('div', buttons);
        },
        reset() {
            this.$refs.form.resetFields();
            return this.query(true);
        },
        save() {},
        openView(row) {
            this.detail.model = true;
            this.detail.title = '查看参数配置';
            this.detail.data = row;
            this.detail.rules.code[2].unique.original = row.code;
            this.detail.readonly = {code: true, name: true, value: true, remark: true, creatorId: true, createdTime: true};
            this.detail.style = {code: 'readonly', name: 'readonly', value: 'readonly', remark: 'readonly', creatorId: 'readonly', createdTime: 'readonly'};
            this.save = null;
        },
        openAdd() {
            this.detail.model = true;
            this.detail.title = '创建参数配置';
            this.detail.data = {};
            this.detail.readonly = false;
            this.detail.style = null;
            this.detail.rules.code[2].unique.original = null;
            this.detail.readonly = {code: false, name: false, value: false, remark: false, creatorId: true, createdTime: true};
            this.detail.style = {code: null, name: null, value: null, remark: null, creatorId: 'hidden', createdTime: 'hidden'};
            this.$refs.form.resetFields();
            this.save = this.add;
        },
        add() {
            this.$refs.detail.validate((valid) => {
                if (!valid) return;
                this.promiseConfirm('确认要创建参数配置么？').then(() => {
                    this.detail.loading = true;
                    let params = Lodash.cloneDeep(this.detail.data);
                    Axios.post(this.url, params)
                        .then(t => {
                            this.detail.model = false;
                            this.query(true);
                        })
                        .finally(t => this.detail.loading = false);
                });
            });
        },
        openModify(row) {
            this.detail.model = true;
            this.detail.title = '修改参数配置';
            this.detail.data = Lodash.merge({}, row);
            this.detail.readonly = {code: true, name: false, value: false, remark: false, creatorId: true, createdTime: true};
            this.detail.style = {code: 'readonly', name: null, value: null, remark: null, creatorId: 'readonly', createdTime: 'readonly'};
            this.detail.rules.code[2].unique.original = row.code;
            this.$refs.form.resetFields();
            this.save = this.modify;
        },
        modify() {
            this.$refs.detail.validate((valid) => {
                if (!valid) return;
                this.promiseConfirm('确认要修改么？').then(() => {
                    this.detail.loading = true;
                    let params = Lodash.cloneDeep(this.detail.data);
                    Axios.put(this.url, params)
                        .then(t => {
                            this.detail.model = false;
                            this.query(true);
                        })
                        .finally(t => this.detail.loading = false);

                });
            });
        },
        remove(row) {
            if (row) {
                row = [row];
            } else {
                let selection = this.$refs.pageTable.selection;
                if (selection.length === 0) return this.$Message.warning('尚未选中任何记录!');
                row = selection;
            }
            this.promiseConfirm('确认要删除么？').then(() => {
                Axios.delete(`${this.url}?${row.map(t => 'id=' + t.id).join('&')}`).then(t => {
                    this.query(true);
                });
            });
        },
    },
    components: {
        Card, Form, FormItem, Input, Icon, Button, InputNumber, DatePicker, PageTable
    },
    mixins: [PromiseConfirm]
};

